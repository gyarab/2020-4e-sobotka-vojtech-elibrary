import { Role } from './role.model';

export class User {
  id: string;
  username: string;
  password: string;
  roles: Role[];
}
