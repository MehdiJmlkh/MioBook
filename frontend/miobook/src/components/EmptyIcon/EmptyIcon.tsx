import "./EmptyIcon.css";

interface Props {
  src: string;
  description: string;
}

const EmptyIcon = ({ src, description }: Props) => {
  return (
    <div className="empty-card">
      <img className="" src={src} alt="" />
      <p>{description}</p>
    </div>
  );
};

export default EmptyIcon;
