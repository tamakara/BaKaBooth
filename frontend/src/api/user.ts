import type {LoginForm, UserSimpleInfoVO} from "@/types/UserTypes";
import axiosInstance from "@/api/index.ts";

export async function login(data: LoginForm): Promise<string> {
    const response = await axiosInstance.post<string>('/user/login', data);
    return response.data;
}

export async function getUserSimpleInfo(): Promise<UserSimpleInfoVO> {
    const response = await axiosInstance.get<UserSimpleInfoVO>('/user/simple-info');
    return response.data;
}