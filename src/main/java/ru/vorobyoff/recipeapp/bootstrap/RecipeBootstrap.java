package ru.vorobyoff.recipeapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.domain.Category;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Note;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;
import ru.vorobyoff.recipeapp.repositories.CategoryRepository;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.Set;

import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;
import static ru.vorobyoff.recipeapp.domain.Difficulty.MODERATE;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(final RecipeRepository recipeRepository, final CategoryRepository categoryRepository,
                           final UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.info("Bootstrap starting.");
        bootstrapRecipes();
    }

    private void bootstrapRecipes() {

        //get UOMs
        final var eachUom = findUnitOfMeasureByDescription("Each");
        final var tableSpoonUom = findUnitOfMeasureByDescription("Tablespoon");
        final var teaspoonUom = findUnitOfMeasureByDescription("Teaspoon");
        final var dashUom = findUnitOfMeasureByDescription("Dash");
        final var pintUom = findUnitOfMeasureByDescription("Pint");
        final var cupsUom = findUnitOfMeasureByDescription("Cup");
        log.info("UOMs have been received.");

        //get Categories
        final var americanCategory = findCategoryByDescription("American");
        final var mexicanCategory = findCategoryByDescription("Mexican");
        log.info("Categories have been received.");

        //Yummy Guac
        final var guacRecipe = Recipe.builder().prepTime(10).cookTime(0).description("Perfect Guacamole")
                .categories(Set.of(americanCategory, mexicanCategory))
                .difficulty(EASY).direction("1 Cut avocado, remove flesh: Cut the avocados in half. " +
                        "Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon\n" +
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
                        "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd").build();

        final var guacNotes = Note.builder().recipeNote("For a very quick guacamole just take a 1/4 cup of " +
                "salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it " +
                "(a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability " +
                "of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. " +
                "Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws").build();

        guacRecipe.setNote(guacNotes);

        guacRecipe.addIngredients(Ingredient.builder().description("minced red onion or thinly sliced green onion").amount(new BigDecimal(2)).recipe(guacRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("serrano chiles, stems and seeds removed, minced").amount(new BigDecimal(2)).recipe(guacRecipe).uom(eachUom).build(),
                Ingredient.builder().description("ripe tomato, seeds and pulp removed, chopped").amount(new BigDecimal(".5")).recipe(guacRecipe).uom(eachUom).build(),
                Ingredient.builder().description("fresh lime juice or lemon juice").amount(new BigDecimal(2)).recipe(guacRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("freshly grated black pepper").amount(new BigDecimal(2)).recipe(guacRecipe).uom(dashUom).build(),
                Ingredient.builder().description("Kosher salt").amount(new BigDecimal(".5")).recipe(guacRecipe).uom(teaspoonUom).build(),
                Ingredient.builder().description("Cilantro").amount(new BigDecimal(2)).recipe(guacRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("ripe avocados").amount(new BigDecimal(2)).recipe(guacRecipe).uom(eachUom).build());

        final var tacosRecipe = Recipe.builder().prepTime(9).cookTime(20).description("Spicy Grilled Chicken Taco")
                .difficulty(MODERATE).direction("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
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
                        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm").build();

        final var tacoNotes = Note.builder().recipeNote("We have a family motto and it is this: Everything " +
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
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ").build();

        tacosRecipe.setNote(tacoNotes);

        tacosRecipe.addIngredients(Ingredient.builder().description("Ancho Chili Powder").amount(new BigDecimal(2)).recipe(tacosRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("cup sour cream thinned with 1/4 cup milk").amount(new BigDecimal(4)).recipe(tacosRecipe).uom(cupsUom).build(),
                Ingredient.builder().description("fresh-squeezed orange juice").amount(new BigDecimal(3)).recipe(tacosRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("finely grated orange zestr").amount(new BigDecimal(1)).recipe(tacosRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("boneless chicken thighs").amount(new BigDecimal(4)).recipe(tacosRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("red onion, thinly sliced").amount(new BigDecimal(".25")).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("cherry tomatoes, halved").amount(new BigDecimal(".5")).recipe(tacosRecipe).uom(pintUom).build(),
                Ingredient.builder().description("medium ripe avocados, slic").amount(new BigDecimal(2)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("Clove of Garlic, Choppedr").amount(new BigDecimal(1)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("Roughly chopped cilantro").amount(new BigDecimal(4)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("radishes, thinly sliced").amount(new BigDecimal(4)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("lime, cut into wedges").amount(new BigDecimal(4)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("small corn tortillasr").amount(new BigDecimal(8)).recipe(tacosRecipe).uom(eachUom).build(),
                Ingredient.builder().description("packed baby arugula").amount(new BigDecimal(3)).recipe(tacosRecipe).uom(cupsUom).build(),
                Ingredient.builder().description("Dried Oregano").amount(new BigDecimal(1)).recipe(tacosRecipe).uom(teaspoonUom).build(),
                Ingredient.builder().description("Dried Cumin").amount(new BigDecimal(1)).recipe(tacosRecipe).uom(teaspoonUom).build(),
                Ingredient.builder().description("Olive Oil").amount(new BigDecimal(2)).recipe(tacosRecipe).uom(tableSpoonUom).build(),
                Ingredient.builder().description("Salt").amount(new BigDecimal(".5")).recipe(tacosRecipe).uom(teaspoonUom).build(),
                Ingredient.builder().description("Sugar").amount(new BigDecimal(1)).recipe(tacosRecipe).uom(teaspoonUom).build());

        tacosRecipe.addCategories(americanCategory, mexicanCategory);

        log.info("Recipes have been created.");

        recipeRepository.save(guacRecipe);
        recipeRepository.save(tacosRecipe);
        log.info("Recipes have been saved.");
    }

    private Category findCategoryByDescription(final String description) {
        return categoryRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected Category Not Found. Description is '" + description + "'."));
    }

    private UnitOfMeasure findUnitOfMeasureByDescription(final String description) {
        return unitOfMeasureRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected Unit Not Found. Unit type is '" + description + "'."));
    }
}