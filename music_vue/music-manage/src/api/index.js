import {get,post} from './http'
import {extendChartView} from "echarts/src/echarts";

// 管理员登录
export const getLoginStatus = (ruleForm) => post(`music_sys/admin/login`,ruleForm);

// ====================歌手相关======================
// 查询所有歌手
export const getSingerList = (current, limit, findObj) => post(`music_sys/singer/findSinger/${current}/${limit}`, findObj);
// 添加歌手
export const addSinger = (registerForm) => post(`music_sys/singer/add`, registerForm);
// 删除歌手
export const deleteSingerById = (id) => get(`music_sys/singer/deleteSinger/${id}`);
// 通过id获取歌手信息
export const getSingerInfo = (id) => get(`music_sys/singer/getSingerById/${id}`);
// 修改歌手信息
export const updateSingerInfo = (registerForm) => post(`music_sys/singer/updateSinger`,registerForm);
// 获取所有歌手
export const getAllSinger = () => get(`music_sys/singer/getAllSinger`);

// ====================歌曲相关======================
// 查询歌手的所有歌曲
export const getSongBySingerId = (id) => get(`music_sys/song/getSongBySingerId/${id}`);
// 添加歌曲
export const addSong = (registerForm) => post(`music_sys/song/addSong`, registerForm)
// 根据id获取歌曲信息
export const getSongById = (id) => get(`music_sys/song/getSongById/${id}`);
// 修改歌曲信息
export const updateSong = (registerForm) => post(`music_sys/song/updateSong`, registerForm)
// 删除歌曲
export const deleteSong = (id) => get(`music_sys/song/deleteSong/${id}`);
// 条件查询带分页
export const findSong = (current,limit,findObj) => post(`music_sys/song/findSong/${current}/${limit}`, findObj);
// 获取歌曲总数量
export const getSongtotal = () => get(`music_sys/song/getSongCount`);
// 通过歌名获取歌曲对象
export const getSongByName = (songName) => get(`music_sys/song/getSongByName/${songName}`);

// ====================歌单相关======================
// 条件查询带分页
export const getSongList = (current, limit, findObj) => post(`music_sys/songList/findSongList/${current}/${limit}`, findObj);
// 添加歌单
export const addSongList = (registerForm) => post(`music_sys/songList/addSongList`, registerForm);
// 根据id获取歌单信息
export const getSongListById = (id) => get(`music_sys/songList/getSongListById/${id}`);
// 修改歌单信息
export const updateSongList = (registerForm) => post(`music_sys/songList/updateSongList`, registerForm);
// 删除歌单
export const deleteSongListById = (id) => get(`music_sys/songList/deleteSongList/${id}`);

// ====================歌单中的歌曲相关======================
// 给歌单添加歌曲
export const addListSong = (songId, songListId) => get(`music_sys/listSong/addListSong/${songId}/${songListId}`,);
// 根据歌单id查询歌曲
export const getListSongById = (song_list_id) => get(`music_sys/listSong/getListSongById/${song_list_id}`);
// 删除歌单中的歌曲
export const deleteListSong = (id) => get(`music_sys/listSong/deleteListSong/${id}`);

// ====================用户相关======================
// 添加用户
export const addConsumer = (registerForm) => post(`music_sys/consumer/addConsumer`, registerForm);
// 通过id获取用户信息
export const getConsumerInfo = (id) => get(`music_sys/consumer/getConsumerInfo/${id}`);
// 修改用户信息
export const updateConsumerInfo = (registerForm) => post(`music_sys/consumer/updateConsumer`, registerForm);
// 删除用户
export const deleteConsumerById = (id) => get(`music_sys/consumer/deleteConsumer/${id}`);
// 条件查询带分页(根据用户名和性别查询）
export const findConsumer = (current, limit, findObj) => post(`music_sys/consumer/findConsumer/${current}/${limit}`, findObj);
// 获取所有用户
export const getAllConsumer = () => get(`music_sys/consumer/getAllConsumer`);

// ====================用户收藏======================
// 删除收藏
export const deleteCollect = (songId,consumerId) => get(`music_sys/collect/deleteCollect/${songId}/${consumerId}`);
// 查询某个用户的收藏列表
export const getCollectByConsumerId = (consumerId) => get(`music_sys/collect/getCollectByConsumerId/${consumerId}`);

// ====================用户评论======================
// 获取指定歌曲或歌单的评论列表
export const getCommentList = (type, id) => get(`music_sys/comment/getComment/${type}/${id}`);
// 删除评论
export const deleteComment = (id) => get(`music_sys/comment/deleteComment/${id}`);
