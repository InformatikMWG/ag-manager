% This class file currently uses some beta in-development LaTeX stuff *not* available over CTAN/TeXLive/etc.
%
% The current drafts can be downloaded at:
%   - https://github.com/SFr682k/credential-cards       (credential-cards class)
%   - https://github.com/SFr682k/fitnames               (fitnames package)
%
% Please note that these files *can't* be added to this repo, since the lppl1.3c is *not* gpl3 compatible

\LoadClass{credential-cards}

\RequirePackage{iftex}
\ifLuaTeX\else
    \ClassError{courser-credcards}{LuaLaTeX is required to compile this document.}{Please switch the LaTeX engine.}
    \stop
\fi

\RequirePackage{array}
\RequirePackage[lastnamefont=sc]{fitnames}

\RequirePackage{fontspec}
\setmainfont{Fira Sans}
\setsansfont{Fira Sans}
\setmonofont{Fira Mono}

\RequirePackage{polyglossia}
\setdefaultlanguage{german}

\RequirePackage[german]{selnolig}

\RequirePackage{url}

\begin{credcardcontent}\small
    Some fancy pic

    \bigskip\bigskip
    Zugangsdaten für:\quad \fitname{\lastname}{\firstname}{10cm}, \group
    
    \begin{center}
        \framebox{\begin{tabular}{l>{\ttfamily}p{.4\textwidth}}
            Benutzername:   & \username \\[\medskipamount]
            Mein Passwort:  & \dotfill
        \end{tabular}}
    \end{center}
    
    \scriptsize
    Sie können sich mit dem oben angegebenen Benutzernamen unter \url{http://foo.example.org/bar} anmelden. \\
    Nach erfolgreicher Anmeldung müssen Sie ein neues Passwort festlegen, dass Sie oben notieren können.
\end{credcardcontent}