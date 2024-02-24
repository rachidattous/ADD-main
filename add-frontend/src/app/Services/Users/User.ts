export interface User {
  id: string;
  firstname: string;
  lastname: string;
  username: string;
  state: UserState;
  roles: string[];
  photo?: string;
  userStatus?: string;
}

export enum UserState {
  NEW = 'New',
  ACTIVE = 'Active',
  BANNED = 'Banned',
}

