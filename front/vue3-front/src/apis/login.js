import httpInstance from '@/utils/http.js'

async function getRegisterCodeAPI(email) {
  try {
    const res = await httpInstance({
      url: '/api/user/send-register-code',
      params: {
        email,
      },
    })
    return res
  } catch (error) {
    console.log(error.message)
    throw error
  }
}

async function UserRegisterAPI(registerDTO) {
  try {
    const res = await httpInstance({
      url: '/api/user/register',
      data: registerDTO,
      method: 'POST',
    })
    return res
  } catch (error) {
    console.log(error.message)
    throw error
  }
}

async function UserLoginAPI(loginDTO) {
  try {
    const res = await httpInstance({
      url: '/api/user/login',
      data: loginDTO,
      method: 'POST',
    })
    return res
  } catch (error) {
    console.log(error.message)
    throw error
  }
}

export { getRegisterCodeAPI, UserRegisterAPI, UserLoginAPI }
