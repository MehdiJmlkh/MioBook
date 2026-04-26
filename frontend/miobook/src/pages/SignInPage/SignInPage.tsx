import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import { FieldValues, useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import "./SignInPage.css";
import Button from "../../components/Button";

const schema = z.object({
  username: z.string().min(1),
  password: z.string().min(4),
});

type FormData = z.infer<typeof schema>;

const SignInPage = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const onSubmit = (data: FieldValues) => console.log(data);

  return (
    <>
      <Form onSubmit={handleSubmit(onSubmit)} type={FormType.SingIn}>
        <Input {...register("username")} placeholder="Username" />
        <PasswordInput {...register("password")} placeholder="Password" />
        <Button disabled={!isValid} className="btn-primary form__btn">
          Sign in
        </Button>
      </Form>
      <Footer />
    </>
  );
};

export default SignInPage;
