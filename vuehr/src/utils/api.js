import  axios from 'axios'
import { Message } from 'element-ui';
import router from '../router/index';
axios.interceptors.response.use(success=>{
    // 如果 sucess.status 存在，并且等于200 ，并且自定义的返回对象中的 status 等于500
    if(success.status && success.status==200 && success.data.status == 500){
        Message.error({message:success.data.msg});
        return;
    }
    if(success.data.msg){
        Message.success({message:success.data.msg})
    }
    return success.data;
},error=>{
    if(error.response.status == 504 || error.response.status == 404){
        Message.error({message:'服务器丢失了'});
    }else if(error.response.status == 403){
        Message.error({message:'权限不足'});
    }else if(error.response.status == 401){
        Message.error({message:'尚未登录'});
        router.replace('/');
    }else{
        // 如果服务端有返回错误信息
        if(error.response.data.msg){
            Message.error({message:error.response.data.msg});
        }else{
            Message.error({message:'未知错误'});
        }
    }
    return;
})

let base='';
// 命名导出
export const postKeyValueRequest = (url,params)=>{
    return axios({
        method: 'post',
        // 占位符
        //base = APP_PATH
        //URL就是请求链接，url=“/emps”
        url:`${base}${url}`,
        data:params,
        // 把参数转成k，v的形式
        transformRequest:[function(data){
            // 遍历 data
            let ret = '';
            for(let i in data){
                console.log(i);
                console.log(data[i]);
                ret+=encodeURIComponent(i)+'='+encodeURIComponent(data[i])+'&'
            }
            console.log(ret);
            return ret;
        }],
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        }
    })
}

export const postRequest=(url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params
    })
}

export const putRequest=(url,params)=>{
    return axios({
        method:'put',
        url:`${base}${url}`,
        data:params
    })
}

export const getRequest=(url,params)=>{
    return axios({
        method:'get',
        url:`${base}${url}`,
        data:params
    })
}

export const deleteRequest=(url,params)=>{
    return axios({
        method:'delete',
        url:`${base}${url}`,
        data:params
    })
}