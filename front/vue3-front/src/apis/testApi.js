import httpInstance from "@/utils/http.js";

function getAllRooms(){
  return   httpInstance({
        url:"/api/room/getAllRooms",
        method:"get"
    })
}
export {
    getAllRooms
}