export interface AddCourseRequest {
  title: string;
  summary: string;
  image: File;
}

export type AddWeekRequest = {
  title: string;
  number: number;
};

export type AddTextRequest = {
  title: string;
  number: number;
  text: string;
};
export type AddContentRequest = {
  title: string;
  number: number;
  description: string;
  contentId: string;
};
export type AddMultimediaRequest = {
  title: string;
  number: number;
  multimedia: string;
};
export type AddQuizRequest = {
  number: number;
  title: string;
  questions: Array<{
    questionContent: string;
    type: string;
    choices: Array<{
      responseContent: string;
      isTrue: string;
    }>;
  }>;
};
