import { zodResolver } from "@hookform/resolvers/zod";
import { AxiosError } from "axios";
import { useState } from "react";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import authService from "../../services/AuthService";
import "./SignInPage.css";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
});

type FormData = z.infer<typeof schema>;

interface ErrorResponse {
  error: string;
}

const SignInPage = () => {
  const {
    register,
    handleSubmit,
    formState: { isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const [error, setError] = useState("");

  const onSubmit = (data: FieldValues) => {
    authService
      .login(data)
      .then((data) => setError(""))
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
        type={FormType.SingIn}
        isValid={isValid}
        error={error}
      >
        <Input {...register("username")} placeholder="Username" />
        <PasswordInput {...register("password")} placeholder="Password" />
      </Form>
      <Footer />
    </>
  );
};

export default SignInPage;
