import Form, { FormType } from "./components/Form";
import Input from "./components/Input";

function App() {
  return (
    <Form type={FormType.SingIn}>
      <Input placeholder="Username"></Input>
      <Input placeholder="Password"></Input>
    </Form>
  );
}

export default App;
