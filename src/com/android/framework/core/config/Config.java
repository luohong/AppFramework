package com.android.framework.core.config;

import android.os.Environment;


public class Config {
	
	// ApiDocs：http://app.zzz4.com/29f9ba2d4e8b39f2e7cf04fd691c2790/ApiDocs
	
	public static String FIRST_OPEN = "first_open";
	
	//屏幕尺寸
	public static int screenWidth = 720, screenHeight = 1280;
	
    //HTTP请求超时时间
    public static final int HTTP_TIMEOUT = 30000;
    //DEBUG SWITCH
    public static final boolean DEBUG = false;
    
    public final static String PFILE_NAME   = "com.demohunter.suipai";
	
	public static final String BASE_URL = "http://app.zzz4.com/api.php?appid=1&appkey=bd3e783d6eec2d977989edc19605009c&method=";
	
	//----------------------------------------API final start----------------------------------
	//获取某人有某人聊天记录列表
	public static final String APICHAT_GETCHATLIST = "ApiChat.getChatList";
	
	//获取某人有某人聊天记录列表
	public static final String APICHAT_GETCHATMESSAGELIST = "ApiChat.getChatMessageList";
	
	
	public static final String APICHAT_SETSTATUS = "ApiChat.setChatStatus";
    
	//发送一条信息到某个用户
    public static final String APICHAT_SENDMSG = "ApiChat.sendMsg";
    
	//获取当前登录用户是否有新消息
    public static final String APICHAT_HASNEWMESSAGE = "ApiChat.hasNewMessage";
    
    //删除和某个人的聊天记录
    public static final String APICHAT_DELCHAT = "ApiChat.delChat";
    
    //版本检测更新
    public static final String APICOMMON_CHECKVERSION = "ApiCommon.checkVersion";
    
    //意见反馈接口
    public static final String APICOMMON_FEEDBACK = "ApiCommon.feedback";
    
    //更改密码
    public static final String APIACCOUNT_CHANGEPWD = "ApiAccount.changePwd";
    
    //更改签名
    public static final String APIACCOUNT_CHANGEDESC = "ApiAccount.changDesc";
    
    //更改性别
    public static final String APIACCOUNT_CHANGESEX = "ApiAccount.changeSex";
    
    //上传头像
    public static final String APIACCOUNT_UPLOADAVATAR = "ApiAccount.uploadAvatar";
    
    public static final String APIACCOUNT_UPLOADPHOTO = "ApiAccount.uploadPhoto";
    
    public static final String APIACCOUNT_DELPHOTO = "ApiAccount.delPhoto";
    
    
    public static final String APIACCOUNT_UPDATEUSERINFO = "ApiAccount.updateUserInfo";
    
    //更改年龄
    public static final String APIACCOUNT_CHANGEAGE = "ApiAccount.changeAge";
    
    /**
     * 登录接口 <br/>
     * POST 参数： <br/>
     * name : 用户名<br/>
     * pwd  : 密码<br/>
     */
    public static final String APIACCOUNT_LOGIN = "ApiAccount.login";
    /**
     * 注册 <br/>
     * name : 注册手机号，只能是手机号注册 <br/>
     * pwd  : 密码 <br/>
     * vpwd : 验证密码 <br/>
     */
    public static final String APIACCOUNT_REG = "ApiAccount.reg";
    
    /**
     * 获取标签列表 <br/>
     * 无参数
     */
    public static final String APITOPIC_GETTAG = "ApiTopic.getTag";
    
    /**
     * 随拍帖子发布接口 <br/>
     * 
     * POST参数
     * tagid   : 标签ID<br/>
     * uid     : 登录用户UID<br/>
     * sign        : 登录用户签名<br/>
     * title       : 随拍帖子标题<br/>
     * content : 随拍帖子内容<br/>
     * pic     : 上传图片字段，可以传多图<br/>
     */
    public static final String APITOPIC_ADD = "ApiTopic.add";
    
    /**
     * 赞某个帖子 <br/>
     * 
     * POST参数
     * uid     : 登录用户UID<br/>
     * sign        : 登录用户签名<br/>
     * tid     : 帖子ID<br/>
     */
    public static final String APITOPIC_DIGG = "ApiTopic.digg";
    
    /**
     * 删除自己发布的帖子<br/>
     * 
     * POST参数
     * uid     : 登录用户UID<br/>
     * sign        : 登录用户签名<br/>
     * tid     : 帖子ID<br/>
     */
    public static final String APITOPIC_DELTOPIC = "ApiTopic.delTopic";
    
    /**
     * 对某个帖子添加评论 <br/>
     * 
     * POST参数
     * uid     : 登录用户UID<br/>
     * sign        : 登录用户签名<br/>
     * tid     : 帖子ID<br/>
     * content     : 评论内容<br/>
     */
    public static final String APITOPIC_ADDCOMMENT = "ApiTopic.addComment";
    
    /**
     * 按条件获取主题列表<br/>
     * offset  : 分页偏移量 必选参数<br/>
	 * limit       : 每页记录数 必选参数<br/>
	 * uid     : 当前登录用户UID 可选参数，当查询好友帖子数据时为必选参数<br/>
	 * sign        : 当前登录用户签名<br/>
	 * sex     : 查看某个性别的用户列表 0：所有 1：只看男 2：只看女<br/>
	 * friend  : 是否只看好友的帖子列表 true：只看好友 false：所有<br/>
	 * tagid       : 可选参数，这个参数有传值时表示只取某个标签相关的帖子列表<br/>
     */
    public static final String APITOPIC_GETLIST  = "ApiTopic.getList";
    
    public static final String APIINDEX_SCREEN  = "ApiIndex.screen";
    
    /**
     * 获取某个用户的好友列表 <br/>
     * 
     * POST参数<br/>
	 * userid  需要获取好友列表的用户UID<br/>
	 * uid     当前用户UID<br/>
	 * sign    当前用户签名，用于判断是否登录<br/>
	 * status  是否在线，传 -1 取所有好友列表 0：不在线 1：在线<br/>
	 * offset  分页偏移量<br/>
	 * limit   分页数<br/>
     */
    public static final String APIUSER_GETFRIENDLIST = "ApiUser.getFriendList";
    
    /**
     * 根据关键字搜索用户<br/>
     * name        关键字<br/>
	 * offset  分页便宜量<br/>
	 * limit       分页数<br/>
     */
    public static final String APIUSER_SEARCHUSERBYNAME = "ApiUser.searchUserByName";
    
    /**
     * 向某人发送添加好友请求<br/>
     * uid         当前登录用户UID<br/>
	 * userid  被添加为好友的UID<br/>
	 * sign        当前登录用户签名，用于验证授权<br/>
     */
    public static final String APIUSER_ADDFRIENDREQUEST = "ApiUser.addFriendRequest";
    
    /**
     * 通过某人的好友请求 <br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
	 *	userid  被通过好友请求的用户UID<br/>
     */
    public static final String APIUSER_PASSFRIENDREQUEST = "ApiUser.passFriendRequest";
    
    /**
     * 获取当前登录用户收到的加好友请求列表 <br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
     */
    public static final String APIUSER_GETFRIENDREQUESTLIST = "ApiUser.getFriendRequestList";
    
    /**
     * 修改用户的备注名 <br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
	 *	userid  被通过好友请求的用户UID<br/>
	 *	name        被修改备注名的用户的备注名<br/>
     */
    public static final String APIUSER_UPDATEFRIENDMARKNAME = "ApiUser.updateFriendMarkName";
    
    
    public static final String APIUSER_DELBLOCK = "ApiUser.delBlockUser";
    
    /**
     * 举报某个用户 <br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
	 *	userid  被通过好友请求的用户UID<br/>
     */
    public static final String APIUSER_REPORTUSER = "ApiUser.reportUser";
    
    /**
     * 拉黑某个用户 <br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
	 *	userid  被通过好友请求的用户UID<br/>
     */
    public static final String APIUSER_BLOCKUSER = "ApiUser.blockUser";
    
    /**
     * 获取被当前登录用户加入到黑名单的用户列表<br/>
     * uid         当前登录用户UID<br/>
	 *	sign        当前登录用户签名<br/>
     */
    public static final String APIUSER_GETBLOCKUSERLIST = "ApiUser.getBlockUserList";
    
    /**
     * 获取所有用户列表
     */
    public static final String APIUSER_GETUSERLIST = "ApiUser.getUserList";
    
    /**
     * 获取某个用户发布的帖子列表
     */
    public static final String APIUSER_GETTOPICLIST = "ApiUser.getTopicList";
    
    /**
     * 获取用户信息
     */
    public static final String APIUSER_GETUSERINFO = "ApiUser.getUserInfo";
    
    /**
     * 删除好友
     */
    public static final String APIUSER_DELFRIEND = "ApiUser.delFriend";
    
    /**
     * 获取某个帖子的评论列表
     */
    public static final String APICOMENT_GETLISTBYTOPICID = "ApiComment.getListByTopicId";
    
    
    
    public static final String APIINDEX_INDEX = "ApiIndex.Index";
    
    
	//----------------------------------------API final end----------------------------------
	
	/**
	 * 获取接口
	 * @param action 接口全称
	 * @return
	 */
	public static String getUrl(String action) {
	    return BASE_URL + action;
	}
	
	
	//-------------------------------------- Cache start--------------------------------------
	//登陆信息存储
	public static final String CACHE_USER_KEY = "cache_user_key";
	
    //标签信息存储
    public static final String CACHE_TAG_KEY = "cache_tag_key";

    //消息提醒设置缓存
	public static final String CACHE_MESSAGE_NOTIFY_KEY = "cache_message_notify_key";
    
    // 缓存周期一天
    public static final int CACHE_ONE_DAY = 24 * 60 * 60;
    
    
    public static final String CACHE_SCREEN_SHOW = "screen_show";
    
    public static final String CACHE_SCREEN_START = "screen_start";
    
    public static final String CACHE_SCREEN_END = "screen_end";
    
    public static final String CACHE_SCREEN_IMAGE = "screen_image";
    
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/suipai/download/";
	
	//-------------------------------------- Cache end--------------------------------------
	
	
}
