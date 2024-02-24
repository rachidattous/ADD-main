import { ContentState, FileType } from './Enums';
export type File = {
  id: string;
  url: string;
  ext: string;
  extension: string;
  uploadDate: string;
  uploadTime: string;
  originalFileName: string;
  path: string;
  fileSize: number;
  fileType: FileType;
  userId: string;
  state: ContentState;
  title: string;
  description: string;
  validations: {
    id: string;
    validationType: ContentState;
    comment: string;
    validationOrder: number;
    userId: string;
    date: string;
    time: string;
  }[];
  validationOrder: number;
};

export interface FileSearchRequest {
  state?: ContentState;
  userId?: string;
  date?: string;
  time?: any;
  fileType?: FileType;
  ext?: string;
  query?: string;
  criteria?: string;
  fileIds?: string[];
}

export interface AddContentRequest {
  title: string;
  description: string;
  file: any;
  userId: string;
}

export interface ReplaceContentRequest {
  title: string;
  description: string;
  file: any;
  userId: string;
}

export interface ContentSuggestionRequest {
  query: string;
  criteria: string[];
}

export interface ContentSearchByIdsRequest {
  contentIds: string[];
}

export interface ContentEvaluationRequest {
  validationType: ContentState;
  comment: string;
  userId: string;
}
