import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        // 服务器传送过来的数据都放在这里
        routes:[]
    },
    mutations:{
        initRoutes(state,data){
            state.routes=data;
        }
    },
    actions:{

    }
})