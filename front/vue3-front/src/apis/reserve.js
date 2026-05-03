import httpInstance from '@/utils/http.js'

async function reserveAPI(Reservation) {
  try {
    const res = await httpInstance({
      url: '/api/reservation/create',
      method: 'POST',
      data: Reservation,
    })
    return res
  } catch (error) {
    console.log(error.message)
    alert('Reservation failed')
    throw error
  }
}

export { reserveAPI }
