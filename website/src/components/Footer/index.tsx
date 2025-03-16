import { Separator } from '../ui/separator';
export const Footer = () => {
  return (
    <div className='relative mt-auto w-full'>
      {/* Section pour mobile */}
      <div className='sm:hidden flex p-8 flex-col gap-8'>
        <Separator />
        <div>
          <p>
            © {new Date().getFullYear()} | Tous droits réservés
            <br />
            VILLET Téo, ENDIGNOUS Arnaud, CHAMPEIX Cédric et DELASSUS Félix
          </p>
        </div>
      </div>

      {/* Section pour écrans plus grands */}
      <div className='hidden sm:block'>
        <Separator />
        <div className='flex justify-between p-4 px-16'>
          <p>© {new Date().getFullYear()} VILLET Téo, ENDIGNOUS Arnaud, CHAMPEIX Cédric et DELASSUS Félix | Tous droits réservés</p>
        </div>
      </div>
    </div>
  );
};
