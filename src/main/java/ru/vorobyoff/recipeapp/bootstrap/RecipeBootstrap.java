package ru.vorobyoff.recipeapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.domain.Category;
import ru.vorobyoff.recipeapp.domain.Difficulty;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Note;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;
import ru.vorobyoff.recipeapp.repositories.CategoryRepository;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(final CategoryRepository categoryRepository, final RecipeRepository recipeRepository, final UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        bootstrapRecipes();
    }

    private void bootstrapRecipes() {

        //get UOMs
        final var eachUom = getUnitOfMeasure("Each");
        final var tableSpoonUom = getUnitOfMeasure("Tablespoon");
        final var teaspoonUom = getUnitOfMeasure("Teaspoon");
        final var dashUom = getUnitOfMeasure("Dash");
        final var pintUom = getUnitOfMeasure("Pint");
        final var cupsUom = getUnitOfMeasure("Cup");

        //get Categories
        final var americanCategory = getCategory("American");
        final var mexicanCategory = getCategory("Mexican");

        //Yummy Guac
        final var guacRecipe = new Recipe("Perfect Guacamole", 10, 0, Difficulty.EASY,
                "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado " +
                        "with a blunt knife and scoop out the flesh with a spoon\n" +
                        "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should" +
                        "be a little chunky.)\n" +
                        "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in " +
                        "the lime juice will provide some balance to the richness of the avocado and will help delay the " +
                        "avocados from turning brown. Add the chopped onion, cilantro, black pepper, and chiles. Chili " +
                        "peppers vary individually in their hotness. So, start with a half of one chili pepper and add " +
                        "to the guacamole to your desired degree of hotness. Remember that much of this is done to taste " +
                        "because of the variability in the fresh ingredients. Start with this recipe and adjust to your " +
                        "taste.\n" +
                        "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole " +
                        "cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn " +
                        "the guacamole brown.) Refrigerate until ready to serve. Chilling tomatoes hurts their flavor, " +
                        "so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        final var guacNotes = new Note(guacRecipe, "For a very quick guacamole just take a 1/4 cup of " +
                "salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it " +
                "(a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability " +
                "of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. " +
                "Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        guacRecipe.setNote(guacNotes);

        guacRecipe.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom, guacRecipe));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        recipeRepository.save(guacRecipe);

        final var tacosRecipe = new Recipe("Spicy Grilled Chicken Taco", 9, 20, Difficulty.MODERATE,
                "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                        "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, " +
                        "oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to " +
                        "make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                        "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                        "\n" +
                        "\n" +
                        "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted " +
                        "into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                        "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high " +
                        "heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs " +
                        "and heat for a few seconds on the other side.\n" +
                        "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                        "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of " +
                        "arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle " +
                        "with the thinned sour cream. Serve with lime wedges.\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        final var tacoNotes = new Note(tacosRecipe, "We have a family motto and it is this: Everything " +
                "goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled " +
                "jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan " +
                "on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet " +
                "orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the " +
                "tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacosRecipe.setNote(tacoNotes);

        tacosRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipeRepository.save(tacosRecipe);
    }

    private Category getCategory(final String american) {
        return categoryRepository.findByDescription(american)
                .orElseThrow(() -> new RuntimeException("Expected Category Not Found"));
    }

    private UnitOfMeasure getUnitOfMeasure(final String each) {
        return unitOfMeasureRepository.findByDescription(each)
                .orElseThrow(() -> new RuntimeException("Expected Unit Not Found. Unit type is '" + each + ""));
    }
}
