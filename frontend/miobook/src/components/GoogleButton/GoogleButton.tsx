import { FcGoogle } from "react-icons/fc";
import Button from "../Button";
import "./GoogleButton.css";

interface Props {
  className: string;
}

const GoogleButton = ({ className }: Props) => {
  const frontendUrl = window.location.origin;
  
  const state = crypto.randomUUID();
  sessionStorage.setItem("oauth_state", state);

  return (
    <Button
      className={`btn-secondary google-btn ${className}`}
      onClick={() => {
        const params = new URLSearchParams({
          client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
          redirect_uri: `${frontendUrl}/auth/google/callback`,
          response_type: "code",
          scope: "openid email profile",
          state: state,
          nonce: "RANDOM_NONCE",
        });
        window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?${params}`;
      }}
    >
      <FcGoogle className="google-btn__icon" />
      Continue with Google
    </Button>
  );
};

export default GoogleButton;
