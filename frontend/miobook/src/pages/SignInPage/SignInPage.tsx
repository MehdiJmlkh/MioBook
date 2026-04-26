import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import { FieldValues, useForm } from "react-hook-form";
import "./SignInPage.css";

const SignInPage = () => {
  const { register, handleSubmit } = useForm();

  const onSubmit = (data: FieldValues) => console.log(data);

  return (
    <>
      <Form onSubmit={handleSubmit(onSubmit)} type={FormType.SingIn}>
        <Input {...register("username")} placeholder="Username" />
        <PasswordInput {...register("password")} placeholder="Password" />
      </Form>
      <Footer />
    </>
  );
};

export default SignInPage;
