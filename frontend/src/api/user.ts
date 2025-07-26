import type {LoginForm} from "@/types/user";
import axiosInstance from "@/api/index.ts";

export async function login(data: LoginForm): Promise<string> {
    const response = await axiosInstance.post<string>('/user/login', data);
    return response.data;
}