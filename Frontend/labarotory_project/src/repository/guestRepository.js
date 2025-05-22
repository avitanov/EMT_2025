import axiosInstance from "../axios/axios.js";


const guestRepository = {

    findAll: async()=>{
        return await axiosInstance.get("/guests")
    },
    findById: async(id)=>{
        return await axiosInstance.get(`/guests/${id}`)
    },
    add: async (data)=>{
        return await axiosInstance.post("/guests/add",data);
    },
    edit: async(id,data)=>{
        return await axiosInstance.put(`/guests/edit/${id}`,data);
    },
    delete: async (id)=>{
        return await axiosInstance.delete(`/guests/delete/${id}`)
    }
}

export default guestRepository;