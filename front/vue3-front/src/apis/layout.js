import httpInstance from "@/utils/http.js";
export function getCategoryAPI() {

    return httpInstance({

        url: '/front/head/category'

    })
}