all: article

article:
	pdflatex betterxml.article
	bibtex betterxml.article
	pdflatex betterxml.article

clean:
	-rm *.aux *.log *.nav *.out *.pdf *.snm *.toc *.vrb *.ps *.dvi
	-rm *~
