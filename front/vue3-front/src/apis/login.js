import httpInstance from "@/utils/http.js";
    async function getRegisterCodeAPI(email){
        try {
            let res= await httpInstance({
                url:"/api/user/send-register-code",
                params:{
                    email:email
                }
            })
            return res.data
        }catch ( error){
            console.log(err.message)
        }
    }
    async function UserRegisterAPI (registerDTO){
        try {
            let res =await httpInstance({
                url:"/api/user/register",
                data:registerDTO,
                method:"POST"
            })
            return res.data
        }catch ( error){
            console.log(err.message)
        }
    }

export {getRegisterCodeAPI,UserRegisterAPI}