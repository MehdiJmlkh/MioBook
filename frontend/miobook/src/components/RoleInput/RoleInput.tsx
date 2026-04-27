import "./RoleInput.css";
import { LuBriefcase } from "react-icons/lu";

import { HiOutlineUserCircle } from "react-icons/hi2";

export enum Role {
  Customer,
  Manager,
}

interface Props {
  role?: Role;
}

const RoleInput = ({ role }: Props) => {
  return (
    <label>
      <input
        type="radio"
        name="role"
        className="d-none"
        defaultChecked={role === Role.Customer}
      />
      <div className="role-option">
        {role === Role.Customer ? (
          <HiOutlineUserCircle className="role__icon" />
        ) : (
          <LuBriefcase className="role__icon" />
        )}
        {role === Role.Customer ? "Custoemr" : "Manager"}
      </div>
    </label>
  );
};

export default RoleInput;
