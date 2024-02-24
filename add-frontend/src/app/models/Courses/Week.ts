export type Week = {
  id: string;
  title: string;
  number: number;
  activities: Activity[];
};

export type Activity = {
  id: string;
  title: string;
  number: number;
  text?: string;
  questions?: any;
  url?: string;
  contentId?: string;
  fileId?: string;
  type?: ActivityType;
};

export enum ActivityType {
  TEXT = 'TEXT',
  CONTENT = 'CONTENT',
  MULTIMEDIA = 'MULTIMEDIA',
}
