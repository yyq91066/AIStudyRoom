import httpInstance from "@/utils/http.js";

export function getAllRooms(){
  return   httpInstance({
        url:"/api/room/getAllRooms",
    })
}
