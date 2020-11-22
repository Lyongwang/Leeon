package com.github.common.event.eventor

/**
 * Created by Lyongwang on 2020/11/22 15: 00.
 *
 * Email: liyongwang@yiche.com
 */
class SocialEventor private constructor(id: String, state: Boolean, num: Int, type: SocialType): IEventor{
    val id = id
    val state = state
    val type = type
    val num = num

    companion object{
        /**
         * 关注/收藏事件 id 关注/收藏id, focus 关注/收藏状态
         */
        fun obtain(id:String, state: Boolean): SocialEventor{
            return SocialEventor(id, state, -1, SocialType.collection)
        }

        /**
         * 点赞事件 id 点赞id, focus 点赞状态, 当前点赞数量
         */
        fun obtain(id:String, state: Boolean, num:Int): SocialEventor{
            return SocialEventor(id, state, num, SocialType.liked)
        }
    }

    /**
     * 社交类型
     */
    enum class SocialType{
        collection, liked
    }
}

