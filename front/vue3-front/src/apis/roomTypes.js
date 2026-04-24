import httpInstance from "@/utils/http.js";
export function getRoomTypesAPI(){
    return   httpInstance({
        url:"/api/room/getRoomTypes",
    })
}
