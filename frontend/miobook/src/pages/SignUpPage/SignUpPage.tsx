import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import authService from "../../services/AuthService";
import { AxiosError } from "axios";
import { useState } from "react";
import "./SignUpPage.css";
import RoleInput from "../../components/RoleInput";
import { Role } from "../../components/RoleInput/RoleInput";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
  email: z.string().email().min(1),
  country: z.string().min(1),
  city: z.string().min(1),
});

type FormData = z.infer<typeof schema>;

interface ErrorResponse {
  error: string;
}

const SignUpPage = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const [error, setError] = useState("");

  const onSubmit = (data: FieldValues) => {
    authService
      .login(data)
      .then((data) => console.log(data))
      .catch((err: AxiosError<ErrorResponse>) => {
        if (err.response?.data.error) {
          setError(err.response?.data.error);
        }
      });
  };

  return (
    <>
      <Form
        onSubmit={handleSubmit(onSubmit)}
        type={FormType.SingUp}
        isValid={isValid}
        error={error}
      >
        <Input {...register("username")} placeholder="Username" />
        <PasswordInput {...register("password")} placeholder="Password" />
        <Input {...register("email")} placeholder="Email" />
        <div className="input-container--1x2">
          <Input {...register("country")} placeholder="Country" />
          <Input {...register("city")} placeholder="City" />
        </div>
        <span className="role__header">I am</span>
        <div className="input-container--1x2">
          <RoleInput role={Role.Customer} />
          <RoleInput role={Role.Manager} />
        </div>
      </Form>
      <Footer />
    </>
  );
};

export default SignUpPage;
