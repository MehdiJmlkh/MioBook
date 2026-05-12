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
  isValid: boolean;
  error?: string;
}

const Form = ({ type, children, onSubmit, isValid, error }: Props) => {
  const title = type === FormType.SingIn ? "Sign in" : "Sign Up";
  return (
    <div className="form">
      <h1 className="form__heading">{title}</h1>
      <h2 className="form__sub-heading">MioBook</h2>

      <form onSubmit={onSubmit} className="form__body">
        {children}

        <div className={type === FormType.SingIn ? "form__btn-container" : ""}>
          {error && <p className="text-danger">{error}</p>}
          <Button disabled={!isValid} className="btn-primary form__btn">
            {title}
          </Button>
        </div>
      </form>

      <div className="form__footer">
        <span>
          {type === FormType.SingIn
            ? "Not a member yet? "
            : "Already have an account? "}
        </span>
        <Link className="link--primary" to={type === FormType.SingIn ? "/sign-up" : "/sign-in"}>
          {type === FormType.SingIn ? "Sign Up" : "Sign in"}
        </Link>
      </div>
    </div>
  );
};

export default Form;
