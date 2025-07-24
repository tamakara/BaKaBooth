export interface LoginForm {
    username: string
    password: string
}

export interface UserStatus {
    id: number
    token: string
    avatar: string
    username: string
    nickname: string
}