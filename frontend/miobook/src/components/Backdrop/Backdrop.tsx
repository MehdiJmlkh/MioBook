import "./Backdrop.css";

interface Props {
  enabled: boolean;
}

const Backdrop = ({ enabled }: Props) => {
  return <div className={`backdrop ${enabled ? "backdrop--enabled" : ""}`} />;
};

export default Backdrop;
