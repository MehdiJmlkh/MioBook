import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { AxiosError } from "axios";
import { useState } from "react";
import "./SignUpPage.css";
import RoleInput from "../../components/RoleInput";
import { Role } from "../../components/RoleInput/RoleInput";
import userService from "../../services/UserService";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
  email: z.string().email().min(1),
  address: z.object({
    country: z.string().min(1),
    city: z.string().min(1),
  }),
  role: z.string(),
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
    console.log(data);

    userService
      .create(data)
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
          <Input {...register("address.country")} placeholder="Country" />
          <Input {...register("address.city")} placeholder="City" />
        </div>
        <span className="role__header">I am</span>
        <div className="input-container--1x2">
          <RoleInput userRole={Role.Customer} {...register("role")} />
          <RoleInput userRole={Role.Manager} {...register("role")} />
        </div>
      </Form>
      <Footer />
    </>
  );
};

export default SignUpPage;
