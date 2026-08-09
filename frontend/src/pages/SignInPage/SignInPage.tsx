import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import { useLogin } from "../../queries/auth/useLogin";
import "./SignInPage.css";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
});

type FormData = z.infer<typeof schema>;

const SignInPage = () => {
  const {
    register,
    handleSubmit,
    formState: { isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const login = useLogin();

  return (
    <div className="sign-in-page-container">
      <div className="sing-in-page">
        <Form
          onSubmit={handleSubmit((data) => {
            login.mutate(data);
          })}
          type={FormType.SingIn}
          isValid={isValid}
          error={login.error?.error}
        >
          <Input {...register("username")} placeholder="Username" />
          <PasswordInput {...register("password")} placeholder="Password" />
        </Form>
      </div>
      <Footer />
    </div>
  );
};

export default SignInPage;
