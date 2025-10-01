export interface LoginFormDTO {
    phone: string
    password: string
}

export interface RegisterFormDTO {
    phone: string
    password: string
    confirmPassword?: string
}

export interface UserVO {
    id: number
    isCurrentUser: boolean
    userStateCode: number
    shopStateCode: number

    username: string
    introduction: string
    announcement: string
    followers: number
    avatarUrl: string
    createdAt: string

    phone: string
}

export interface ShopManageVO {
    state: string
    description: string
    followers: number
}

export interface SellerVO {
    username: string
    state: string
    description: string
    followers: number
    avatarUrl: string
}
