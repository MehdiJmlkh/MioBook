import { useEffect } from "react";

export const useNoScroll = (noScroll: boolean[]) => {
  useEffect(() => {
    if (noScroll) {
      document.body.classList.add("no-scroll");
    } else {
      document.body.classList.remove("no-scroll");
    }

    return () => {
      document.body.classList.remove("no-scroll");
    };
  }, noScroll);
};
