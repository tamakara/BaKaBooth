import type {LoginFormDTO, RegisterFormDTO, ShopManageVO, UserSimpleInfoVO} from "@/types/UserTypes";
import axiosInstance from "@/api/index.ts";
import type {SellerVO} from "@/types/UserTypes";

export async function login(data: LoginFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/login', data);
    return response.data;
}

export async function register(data:RegisterFormDTO): Promise<string> {
    const response = await axiosInstance.post<string>('/user/register', data);
    return response.data;
}

export async function getUserSimpleInfo(): Promise<UserSimpleInfoVO> {
    const response = await axiosInstance.get<UserSimpleInfoVO>('/user/vo/simple-info');
    return response.data;
}

export async function getShopManagePageVO(): Promise<ShopManageVO> {
    const response = await axiosInstance.get<ShopManageVO>('/user/vo/shop-manage-page');
    return response.data;
}

export async function getSellerUserVO(userId: string): Promise<SellerVO> {
    const response = await axiosInstance.get<SellerVO>(`/user/vo/seller-user/${userId}`);
    return response.data;
}

export async function favoriteItem(itemId: string): Promise<number> {
    const response = await axiosInstance.post<number>(`/user/favorite/${itemId}`);
    return response.data;
}
