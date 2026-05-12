import { zodResolver } from "@hookform/resolvers/zod";
import { AxiosError } from "axios";
import { useState } from "react";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import RoleInput from "../../components/RoleInput";
import { Role } from "../../components/RoleInput/RoleInput";
import { useAddUser } from "../../queries/useAddUser";
import "./SignUpPage.css";
import { useNavigate } from "react-router-dom";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
  email: z.string().min(1),
  address: z.object({
    country: z.string().min(1),
    city: z.string().min(1),
  }),
  role: z.string(),
});

type FormData = z.infer<typeof schema>;

const SignUpPage = () => {
  const {
    register,
    handleSubmit,
    formState: { isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const addUser = useAddUser();
  const navigate = useNavigate();

  return (
    <div className="sign-up-container">
      <div className="sign-up-page">
        <Form
          onSubmit={handleSubmit((data) => {
            addUser.mutate(data);
          })}
          type={FormType.SingUp}
          isValid={isValid}
        >
          <Input
            {...register("username")}
            placeholder="Username"
            error={addUser.error?.username}
          />

          <PasswordInput {...register("password")} placeholder="Password" />

          <Input
            {...register("email")}
            placeholder="Email"
            error={addUser.error?.email}
          />

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
      </div>
      <Footer />
    </div>
  );
};

export default SignUpPage;
