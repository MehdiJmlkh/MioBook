import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import PasswordInput from "../../components/PasswordInput";
import "./SignInPage.css";

const SignInPage = () => {
  return (
    <>
      <Form type={FormType.SingIn}>
        <Input placeholder="Username" />
        <PasswordInput placeholder="Password" />
      </Form>
      <Footer />
    </>
  );
};

export default SignInPage;
