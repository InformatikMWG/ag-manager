

\LoadClass{scrartcl}


\usepackage[margin=2.5cm]{geometry}
\usepackage{scrlayer-scrpage}


\RequirePackage{iftex}
\ifLuaTeX\else
    \ClassError{courser-credcards}{LuaLaTeX is required to compile this document.}{Please switch the LaTeX engine.}
    \stop
\fi

\RequirePackage{array}
\RequirePackage{graphicx}
\RequirePackage{graphbox}
\RequirePackage{longtable}

\PassOptionsToPackage{table}{xcolor}
\RequirePackage{xcolor}


\PassOptionsToPackage{lastnamefirst, lastnamefont=sc}{fitnames}
\RequirePackage{fitnames}

\RequirePackage{fontspec}
\setmainfont[Numbers={Lining,Tabular}]{Fira Sans}
\setsansfont[Numbers={Lining,Tabular}]{Fira Sans}
\setmonofont{Fira Mono}


\RequirePackage{fontawesome}


\RequirePackage{polyglossia}
\setdefaultlanguage{german}

\PassOptionsToPackage{german}{selnolig}
\RequirePackage{selnolig}

\RequirePackage{url}


\pagestyle{scrheadings}
% 
\setkomafont{pageheadfoot}{\footnotesize\rmfamily}
% \ifoot{}
\cfoot{}
% \chead{}
\ohead{\thepage}
\ofoot{\timestamp}



\newcount\hour
\newcount\minute
\hour = \time
\divide\hour by 60
\minute = \hour
\multiply\minute by 60
\advance\minute by -\time
\minute = -\minute

\edef\timestamp{%
    \ifnum\day<10{0}\fi\the\day.%
    \ifnum\month<10{0}\fi\the\month.%
    \the\year,~%
    %
    \ifnum\hour<10{0}\fi\the\hour:%
    \ifnum\minute<10{0}\fi\the\minute
}


\newcount\listindex
\newcount\lastrowodd

\def\arraystretch{1.3}

\parindent0pt

\definecolor{MWGRed}{RGB}{100,29,27}


% \AtBeginDocument{%
%     \thispagestyle{empty}
%     Some fancy general information \& advertizing stuff
%     \clearpage
%     \setcounter{page}{1}}
    
    
\def\regentry#1#2#3{%
    % check wheter the previous row was odd (1 = true, 0 = false)
    % We have to check here because using \relax (which is necessary for calculation)
    % before printing contents to the table makes LaTeX throw “Misplaced \noalign” errors
    \ifnum\lastrowodd=1\rowcolor{MWGRed!17}\fi%
    \global\advance\listindex 1\relax%
    %
    % calculation:  \listindex % 2
    \global\lastrowodd=\listindex\relax%
    \global\divide\lastrowodd by 2\relax%
    \global\multiply\lastrowodd by 2\relax%
    \global\multiply\lastrowodd by -1\relax%
    \global\advance\lastrowodd by \listindex\relax%
    %
    \the\listindex & \fitname{#1}{#2}{.8\textwidth} (#3) \\\hline}


\def\courserreg@pdescr{A Quite Short Project Description}
\def\courserreg@datetime{St. Nimmerleins-Tag 2048, 12:00-15:00}
\def\courserreg@pnr{42}
\def\courserreg@ptitle{Project Title}
\def\courserreg@attfee{1,00 €}





\newenvironment{register}{%
    \clearpage%
%     \dotfill
    
    \begin{minipage}[t]{.825\linewidth}
        \textbf{\LARGE\scshape\courserreg@pnr.~~\courserreg@ptitle} \\
        %
        \begin{center}
            \begin{tabular}{*{3}{cp{.225\linewidth}}}
                \faInfo      & \multicolumn{5}{p{.8\linewidth}}{\itshape\courserreg@pdescr} \\[\bigskipamount]
                %
                \faCalendar  & \multicolumn{5}{p{.8\linewidth}}{\courserreg@datetime} \\
                \faUsers     & 21~~(42)           &
                \faMoney     & \courserreg@attfee &
                \faMapMarker & r215 \\
            \end{tabular}
        \end{center}

        
    \end{minipage}
    \begin{minipage}[t]{.175\linewidth}
        \includegraphics[align=t, width=\linewidth]{mwglogo}
    \end{minipage}
    
    
    \global\listindex=0\relax
    \global\lastrowodd=0\relax
    \longtable{|>{\hfill}p{.05\textwidth}|p{.9\textwidth}|}%
        \hline%
        \rowcolor{MWGRed}
        \color{white}\textbf{Nr.} & \color{white}\textbf{Name} \\\hline\hline\hline\hline%
        \endfirsthead%
        %
        \multicolumn{2}{r}{\footnotesize\textit{(continued)}} \\\hline%
        \rowcolor{MWGRed}
        \color{white}\textbf{Nr.} & \color{white}\textbf{Name} \\\hline\hline\hline\hline%
        \endhead%
        %
}{%
    \endlongtable%
}