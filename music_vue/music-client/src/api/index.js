import {get,post} from './http'
import {extendChartView} from "echarts/src/echarts";
import Axios from "axios";

// ====================歌手相关======================
// 通过id获取歌手信息
export const getSingerInfo = (id) => get(`music_sys/singer/getSingerById/${id}`);
// 获取所有歌手
export const getAllSinger = () => get(`music_sys/singer/getAllSinger`);
// 根据歌手性别查询歌手
export const getSingerBySex = (sex) => get(`music_sys/singer/getSingerBySex/${sex}`);
// 根据歌手姓名模糊查询歌手
export const getSingerByName = (name) => get(`music_sys/singer/getSingerByName/${name}`);

// ====================歌曲相关======================
// 查询歌手的所有歌曲
export const getSongBySingerId = (id) => get(`music_sys/song/getSongBySingerId/${id}`);
// 根据id获取歌曲信息
export const getSongById = (id) => get(`music_sys/song/getSongById/${id}`);
// 通过歌名模糊查询歌曲
export const getSongByN = (keywords) => get(`music_sys/song/getSongByN/${keywords}`);


// ====================歌单相关======================
// 根据id获取歌单信息
export const getSongListById = (id) => get(`music_sys/songList/getSongListById/${id}`);
// 获取所有歌单
export const getAllSongList = () => get(`music_sys/songList/getAllSongList`);
// 通过标题模糊查询歌单列表
export const findSongListByT = (keywords) => get(`music_sys/songList/findSongListByT/${keywords}`);
// 通过风格查询歌单列表
export const findSongListByS = (style) => get(`music_sys/songList/findSongListByS/${style}`);

// ====================歌单中的歌曲相关======================
// 根据歌单id查询歌曲名
export const getListSongById = (song_list_id) => get(`music_sys/listSong/getListSongById/${song_list_id}`);
// 根据歌单id查询歌曲信息
export const getSongByListSongId = (song_list_id) => get(`music_sys/listSong/getSongByListSongId/${song_list_id}`);

// ====================用户相关======================
// 通过id获取用户信息
export const getConsumerInfo = (id) => get(`music_sys/consumer/getConsumerInfo/${id}`);
// 修改用户信息
export const updateConsumer = (userInfo) => post(`music_sys/consumer/updateConsumer`,userInfo);
// 获取所有用户
export const getAllConsumer = () => get(`music_sys/consumer/getAllConsumer`);
// 用户注册
export const signUp = (resigerForm) => post(`music_sys/consumer/addConsumer`, resigerForm)
// 账号登录
export const loginIn = (resigerForm) => post('music_sys/consumer/loginIn',resigerForm)

// =====================================================================================================
// 下载音乐
export const download = (url) => Axios({
  method: 'get',
  url: url,
  responseType: 'blob'
})

// ====================用户评价======================
// 提交评价
export const submitEvaluate = (evaluateInfo) => post(`music_sys/evaluate/submitEvaluate`,evaluateInfo);
// 获取歌单评分
export const getSongListScore = (songListId) => get(`music_sys/evaluate/getAverageScore/${songListId}`);
// 通过用户id个歌单id获取用户对歌单的评分
export const getUserEvaluate = (evaluateInfo) => post(`music_sys/evaluate/getUserEvaluate`, evaluateInfo)

// ====================用户评论======================
// 提交评论
export const submitComment = (commentInfo) => post(`music_sys/comment/submitComment`,commentInfo);
// 点赞
export const likeComment = (commentInfo) => post(`music_sys/comment/likeComment`,commentInfo);
// 获取指定歌曲或歌单的评论列表
export const getCommentList = (type, id) => get(`music_sys/comment/getComment/${type}/${id}`);

// ====================用户收藏======================
// 添加收藏
export const addCollect = (collectInfo) => post(`music_sys/collect/addCollect`,collectInfo);
// 删除收藏
export const deleteCollect = (id) => get(`music_sys/collect/deleteCollect/${id}`);
// 查询某个用户的收藏列表
export const getCollectByConsumerId = (consumerId) => get(`music_sys/collect/getCollectByConsumerId/${consumerId}`);

