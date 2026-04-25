import Footer from "../../components/Footer";
import Form, { FormType } from "../../components/Form";
import Input from "../../components/Input";
import "./SignInPage.css";

const SignInPage = () => {
  return (
    <>
      <Form type={FormType.SingIn}>
        <Input placeholder="Username"></Input>
        <Input placeholder="Password"></Input>
      </Form>
      <Footer></Footer>
    </>
  );
};

export default SignInPage;
