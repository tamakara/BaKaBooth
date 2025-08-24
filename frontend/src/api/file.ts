import type {FileVO} from "@/types/FileTypes";
import axiosInstance from "@/api/index.ts";

export async function getFileVO(fileId: number): Promise<FileVO> {
    const response =
        await axiosInstance.get<FileVO>('/file/vo/file', {params: {fileId}});
    return response.data;
}