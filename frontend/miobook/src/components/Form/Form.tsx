import { FormEvent, ReactNode } from "react";
import Button from "../Button";
import Link from "../Link";
import "./Form.css";

export enum FormType {
  SingIn,
  SingUp,
}

interface Props {
  type: FormType;
  children?: ReactNode;
  onSubmit?: (event: FormEvent) => void;
}

const Form = ({ type, children, onSubmit }: Props) => {
  const title = type === FormType.SingIn ? "Sign in" : "Sign Up";
  return (
    <div className="form">
      <h1 className="form__heading">{title}</h1>
      <h2 className="form__sub-heading">MioBook</h2>

      <form onSubmit={onSubmit} className="form__body">
        {children}
      </form>

      <div className="form__footer">
        <span>
          {type === FormType.SingIn
            ? "Not a member yet? "
            : "Already have an account? "}
        </span>
        <Link>{type === FormType.SingIn ? "Sign Up" : "Sign in"}</Link>
      </div>
    </div>
  );
};

export default Form;
