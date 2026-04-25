import { ReactNode } from "react";
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
}

const Form = ({ type, children }: Props) => {
  const title = type === FormType.SingIn ? "Sign in" : "Sign Up";
  return (
    <div className="form">
      <h1 className="form__heading">{title}</h1>
      <h2 className="form__sub-heading">MioBook</h2>

      <form action="" className="form__body">
        {children}
        <Button className="btn-primary form__btn">{title}</Button>
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
