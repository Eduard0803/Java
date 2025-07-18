package app;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory({Funcional.class, Excecao.class})
@Suite.SuiteClasses({ ExcecaoTest.class, ParcelaCalculoTest.class, ParcelaTest.class })
public class AllTests {}
