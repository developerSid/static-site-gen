

## Directory structure
1. theme/
   1. root.html -> freemarker template for the root of the site, has special meaning
   2. about.html -> freemarker template that matches up with the about.md
   3. posts.html -> freemarker template that matches up with the posts directory.
2. site/
   1. root.md -> root of the site, has special meaning won't render as sub-directory, will result in index.html
   2. about.md -> another page, will render as a sub-directory
   3. posts/
      1. dec-1-2020.md
      2. dec-2-2020.md
3. generator.properties
