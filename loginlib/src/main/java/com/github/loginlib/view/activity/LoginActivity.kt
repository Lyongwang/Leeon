package com.github.loginlib.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.view.View
import android.view.inputmethod.EditorInfo
import com.github.common.base.BaseActivity
import com.github.loginlib.R
import com.github.loginlib.bean.LoginType
import com.github.loginlib.presenter.LoginPresenter
import com.github.loginlib.tools.PreferenceUtil
import com.github.loginlib.view.contract.LoginContract
import kotlinx.android.synthetic.main.login_activity.*

/**
 * Created by Lyongwang on 2020/9/9 16: 05.
 *
 * Email: liyongwang@yiche.com
 */
class LoginActivity : LoginContract.IView, BaseActivity(), View.OnClickListener {
    private var mPhoneNum: String = ""
    private var mDynamicNum: String = ""
    private var mUsername: CharSequence = ""
    private lateinit var mPresenter: LoginContract.IPresenter
    private var mLoginType: LoginType = LoginType.YICHE_LOGIN

    companion object LoginStart {
        @JvmStatic
        @JvmOverloads
        fun start(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        mPresenter = LoginPresenter(this)
        showLogin()
        initView()
    }

    private fun initView() {
        btn_login.setOnClickListener(this)
        tv_change_login.setOnClickListener(this)
        et_dynamic_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setLoginEnable()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun setLoginEnable() {
        val phoneNum = et_phone_number.text.toString().trim()
        val pwd = et_dynamic_pwd.text.toString().trim()
        btn_login.isEnabled = phoneNum.isNotEmpty() && pwd.isNotEmpty()
    }

    private fun showLogin() {
        if (mLoginType === LoginType.YICHE_LOGIN) {
            showUserNameLogin(false)
        } else {
            showDynamicLogin(false)
        }
    }

    private fun showDynamicLogin(clickToChangLoginType: Boolean) {
        tv_title.text = getString(R.string.quick_loginregistr)
        tv_change_login.text = getString(R.string.account_login)
        if (clickToChangLoginType) {
            mUsername = et_phone_number.text.toString().trim()
        }
        et_phone_number.hint = getString(R.string.login_please_input_phone_number)
        et_phone_number.inputType = InputType.TYPE_CLASS_PHONE
        val phoneInputFilter = arrayOf(EmptyInputFilter(), InputFilter.LengthFilter(resources.getInteger(R.integer.login_phone_number_length)))
        et_phone_number.filters = phoneInputFilter
        et_phone_number.setText(if (mDynamicNum.isEmpty()) "" else mDynamicNum)
        // 点击用户隐私协议不需要清除动态密码
        if (clickToChangLoginType) {
            et_dynamic_pwd.setText("")
        }
        et_dynamic_pwd.hint = getString(R.string.login_please_input_dynamic_pwd)
        et_dynamic_pwd.inputType = EditorInfo.TYPE_CLASS_NUMBER
        val dynamicInputFilter = arrayOf(EmptyInputFilter(), InputFilter.LengthFilter(resources.getInteger(R.integer.login_dynamic_pwd_length)))
        et_dynamic_pwd.filters = dynamicInputFilter
        btn_get_msg_code.visibility = View.VISIBLE
        tv_forget_pwd.visibility = View.GONE
        if (mPhoneNum.isEmpty()) {
            et_phone_number.isFocusable = true
            et_phone_number.requestFocus()
        } else {
            et_phone_number.post {
                et_phone_number.setText(mPhoneNum)
                et_phone_number.requestLayout()
                et_phone_number.isFocusable = true
                et_phone_number.requestFocus()
            }
        }
    }

    private fun showUserNameLogin(clickToChangLoginType: Boolean) {
        if (clickToChangLoginType) {
            mDynamicNum = et_phone_number.text.toString().trim()
        }
        tv_title.text = getString(R.string.account_password_login)
        tv_change_login.text = getString(R.string.quick_loginregistr)
        et_phone_number.hint = getString(R.string.login_please_input_username)
        et_phone_number.inputType = EditorInfo.TYPE_CLASS_TEXT
        val emptyInputFilter = arrayOf<InputFilter>(EmptyInputFilter())
        et_phone_number.filters = emptyInputFilter

        if (mUsername.isEmpty()) {
            et_phone_number.setText(PreferenceUtil.LOGIN_INPUT_ACOUNT_NAME)
        } else {
            et_phone_number.setText(mUsername)
        }
        et_dynamic_pwd.setText("")
        et_dynamic_pwd.hint = getString(R.string.login_please_input_pwd)
        et_dynamic_pwd.inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
        et_dynamic_pwd.filters = emptyInputFilter

        btn_get_msg_code.visibility = View.GONE
        tv_forget_pwd.visibility = View.VISIBLE

        if (et_phone_number.text.toString().isNotEmpty()) {
            et_dynamic_pwd.isFocusable = true;
            et_dynamic_pwd.requestFocus();
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_change_login -> {
                if (mLoginType === LoginType.YICHE_LOGIN) {
                    showDynamicLogin(true)
                    mLoginType = LoginType.DYNAMIC_LOGIN
                } else {
                    showUserNameLogin(true)
                    mLoginType = LoginType.YICHE_LOGIN
                }
            }
            R.id.btn_login -> {
                if (mLoginType == LoginType.YICHE_LOGIN){
                    mPresenter.loginByName(et_phone_number.text.trim().toString(), et_dynamic_pwd.text.trim().toString())
                } else if (mLoginType == LoginType.DYNAMIC_LOGIN) {
                    mPresenter.loginByDynamicPwd(et_phone_number.text.trim().toString(), et_dynamic_pwd.text.trim().toString())
                }
            }
        }
    }

    override fun loginSuccess() {
        PreferenceUtil.LOGIN_INPUT_ACOUNT_NAME = et_phone_number.text.trim().toString()
        finish()

    }

    override fun loginError() {
        TODO("Not yet implemented")
    }
}

class EmptyInputFilter : InputFilter {
    override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int): CharSequence? {
        var result: CharSequence? = null
        if (p0 == " ") {
            result = ""
        }
        return result
    }
}
