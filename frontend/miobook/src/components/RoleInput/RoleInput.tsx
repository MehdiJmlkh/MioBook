import "./RoleInput.css";
import { LuBriefcase } from "react-icons/lu";
import { HiOutlineUserCircle } from "react-icons/hi2";
import React from "react";

export enum Role {
  Customer = "customer",
  Manager = "manager",
}

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  userRole: Role;
}

const RoleInput = React.forwardRef<HTMLInputElement, Props>(
  ({ userRole, ...rest }, ref) => {
    return (
      <label>
        <input
          type="radio"
          name="role"
          ref={ref}
          value={userRole}
          className="d-none"
          defaultChecked={userRole === Role.Customer}
          {...rest}
        />
        <div className="role-option">
          {userRole === Role.Customer ? (
            <HiOutlineUserCircle className="role__icon" />
          ) : (
            <LuBriefcase className="role__icon" />
          )}

          {userRole === Role.Customer ? "Customer" : "Manager"}
        </div>
      </label>
    );
  },
);

export default RoleInput;
