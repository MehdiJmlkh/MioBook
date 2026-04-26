import "./PasswordInput.css";
import "../Input/Input.css";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { useState } from "react";

interface Props {
  placeholder?: string;
}

const PasswordInput = ({ placeholder }: Props) => {
  const [visiable, setVisibale] = useState(false);

  return (
    <div className="password-input">
      <input
        className="form-control"
        placeholder={placeholder}
        type={visiable ? "text" : "password"}
      />

      <span onClick={() => setVisibale(!visiable)}>
        {visiable ? (
          <AiOutlineEyeInvisible className="password-input__icon" />
        ) : (
          <AiOutlineEye className="password-input__icon" />
        )}
      </span>
    </div>
  );
};

export default PasswordInput;
