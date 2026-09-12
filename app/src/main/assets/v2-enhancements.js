(() => {
  'use strict';

  const RECIPES = {
    'Paneer Butter Masala': {
      ingredients: [
        ['Paneer', '250 g'], ['Tomatoes', '3 medium'], ['Onion', '1 medium'],
        ['Cream', '100 ml'], ['Butter', '2 tbsp'], ['Garlic', '4 cloves'],
        ['Ginger', '1 inch'], ['Garam masala', '1 tsp'], ['Chilli powder', '1 tsp'],
        ['Kasuri methi', '1 tsp'], ['Salt', '¾ tsp']
      ],
      steps: [
        'Finely chop the onion, tomatoes, ginger and garlic.',
        'Cut 250 g paneer into bite-size cubes. Keep it aside.',
        'Heat 2 tbsp butter in a pan over medium heat and sauté the onion until soft.',
        'Add ginger and garlic; cook for about 1 minute until fragrant.',
        'Add the tomatoes, chilli powder and salt. Cook until the tomatoes become soft.',
        'Blend the cooked mixture until smooth, then return the sauce to the pan.',
        'Add a little water if needed and simmer the sauce for 3–4 minutes.',
        'Stir in 100 ml cream, garam masala and kasuri methi.',
        'Add the paneer and gently simmer for 5–8 minutes so it absorbs the sauce.',
        'Taste, adjust salt and garam masala, then serve hot with roti or rice.'
      ]
    },
    'Paneer Tikka': {
      ingredients: [['Paneer','250 g'],['Curd','120 g'],['Capsicum','1'],['Onion','1'],['Chilli powder','1 tsp'],['Garam masala','1 tsp'],['Turmeric','½ tsp'],['Lemon juice','1 tbsp'],['Oil','1 tbsp'],['Salt','¾ tsp']],
      steps: ['Cut paneer, capsicum and onion into similar bite-size pieces.','Whisk curd until smooth and add chilli powder, garam masala, turmeric and salt.','Mix in lemon juice and oil to make the marinade.','Add paneer, capsicum and onion and coat everything evenly.','Cover and marinate for at least 20 minutes.','Thread the paneer and vegetables onto skewers or place them on a lined tray.','Cook in a hot oven, air fryer or pan until the edges are lightly charred.','Turn the pieces during cooking so they brown evenly.','Brush with a little oil if the pieces look dry.','Serve hot with lemon wedges and your preferred chutney.']
    },
    'Creamy Tomato Pasta': {
      ingredients: [['Pasta','200 g'],['Tomatoes','2 medium'],['Garlic','3 cloves'],['Cream','120 ml'],['Cheese','50 g'],['Butter','1 tbsp'],['Chilli flakes','½ tsp'],['Italian seasoning','1 tsp'],['Salt','¾ tsp']],
      steps: ['Bring a large pot of salted water to a boil.','Cook 200 g pasta until al dente, then reserve about ½ cup pasta water and drain.','Finely chop the garlic and tomatoes.','Melt 1 tbsp butter in a pan over medium heat.','Add garlic and cook for about 30 seconds without browning it.','Add tomatoes, chilli flakes, Italian seasoning and salt; cook until the tomatoes soften.','Blend or mash the tomato mixture until you have a smooth sauce.','Lower the heat and stir in 120 ml cream.','Add 50 g cheese and a splash of reserved pasta water; stir until smooth.','Toss in the cooked pasta, coat well and serve hot with extra cheese if desired.']
    },
    'Veg Fried Rice': {
      ingredients: [['Cooked rice','3 cups'],['Carrot','1'],['Peas','½ cup'],['Onion','1'],['Soy sauce','1½ tbsp'],['Garlic','3 cloves'],['Oil','1 tbsp'],['Salt','½ tsp']],
      steps: ['Cook the rice beforehand and let it cool so the grains stay separate.','Finely chop the onion, carrot and garlic.','Heat 1 tbsp oil in a large wok or pan over high heat.','Add onion and garlic and stir-fry for about 1 minute.','Add carrot and peas and cook until the vegetables are just tender.','Add the cooled cooked rice and toss everything together.','Pour 1½ tbsp soy sauce around the hot pan and mix well.','Stir-fry for 2–3 minutes without crushing the rice grains.','Taste and add salt only if needed because soy sauce is already salty.','Serve immediately while hot.']
    },
    'Paneer Sandwich': {
      ingredients: [['Bread','4 slices'],['Paneer','150 g'],['Onion','½'],['Tomato','½'],['Cheese','2 slices (40 g)'],['Butter/oil','1 tsp'],['Salt','¼ tsp']],
      steps: ['Crumble 150 g paneer into a bowl.','Finely chop the onion and tomato.','Mix paneer with onion, tomato and salt.','Spread the paneer filling evenly over two bread slices.','Place a cheese slice over each filling.','Cover with the remaining bread slices.','Heat a pan or sandwich maker and lightly grease it with butter or oil.','Toast the sandwiches until both sides are golden and crisp.','Press gently so the cheese melts without crushing the filling.','Cut in half and serve hot.']
    },
    'Chole Masala': {
      ingredients: [['Cooked chickpeas','1½ cups (250 g)'],['Tomatoes','2'],['Onion','1'],['Garlic','4 cloves'],['Ginger','1 inch'],['Spices','2 tsp'],['Oil','1 tbsp'],['Salt','¾ tsp']],
      steps: ['Drain and rinse the cooked chickpeas.','Finely chop the onion, tomatoes, ginger and garlic.','Heat 1 tbsp oil in a pan.','Sauté onion until golden and soft.','Add ginger and garlic and cook for 1 minute.','Add tomatoes, salt and the spices; cook until the tomatoes break down.','Mash a small portion of the chickpeas to help thicken the gravy.','Add all chickpeas and enough water to reach your preferred consistency.','Simmer for 10–15 minutes, stirring occasionally.','Taste and adjust seasoning before serving with rice, roti or bhatura.']
    },
    'Masala Omelette': {
      ingredients: [['Eggs','2'],['Onion','¼'],['Tomato','½'],['Green chilli','1'],['Oil/butter','1 tsp'],['Salt','¼ tsp']],
      steps: ['Finely chop the onion, tomato and green chilli.','Crack 2 eggs into a bowl.','Add the chopped vegetables and salt.','Beat the mixture until the eggs are well combined.','Heat 1 tsp oil or butter in a non-stick pan over medium heat.','Pour in the egg mixture and spread it evenly.','Let the bottom set for 1–2 minutes.','Fold or flip the omelette carefully.','Cook the second side until fully set.','Slide onto a plate and serve immediately.']
    },
    'Paneer Power Salad': {
      ingredients: [['Paneer','150 g'],['Lettuce','2 cups'],['Tomato','1'],['Curd','½ cup (120 g)'],['Lemon juice','1 tbsp'],['Salt','¼ tsp']],
      steps: ['Wash and dry the lettuce and tomato.','Cut the paneer into bite-size cubes.','Chop the tomato and tear the lettuce into smaller pieces.','Whisk ½ cup curd with lemon juice and salt.','Add the lettuce and tomato to a large bowl.','Add the paneer cubes.','Pour over the curd dressing.','Toss gently so the paneer stays intact.','Taste and adjust lemon juice or salt.','Serve immediately while fresh and chilled.']
    },
    'Poha': {
      ingredients: [['Poha','2 cups (180 g)'],['Onion','1'],['Peanuts','⅓ cup'],['Lemon','½'],['Green chilli','1'],['Oil','1 tbsp'],['Salt','½ tsp']],
      steps: ['Rinse 2 cups poha quickly under water and drain well.','Let the poha rest for 5–10 minutes so it softens.','Finely chop the onion and green chilli.','Heat 1 tbsp oil in a pan and roast the peanuts until lightly browned.','Add onion and green chilli and sauté until the onion softens.','Add the softened poha and salt.','Mix gently so the poha does not turn mushy.','Cover and cook on low heat for 2–3 minutes.','Squeeze the juice of ½ lemon over the poha and mix.','Serve hot, optionally topped with fresh coriander.']
    }
  };

  function clean(s) { return (s || '').replace(/\s+/g, ' ').trim(); }

  function findRecipe() {
    const titleEl = document.querySelector('.detailbody h1, .detailbody h2, .detailbody .detailtitle, .detailbody .title');
    const title = clean(titleEl && titleEl.textContent);
    for (const key of Object.keys(RECIPES)) {
      if (title.toLowerCase().includes(key.toLowerCase()) || key.toLowerCase().includes(title.toLowerCase())) return [key, RECIPES[key]];
    }
    const body = clean(document.body.textContent).toLowerCase();
    for (const key of Object.keys(RECIPES)) if (body.includes(key.toLowerCase())) return [key, RECIPES[key]];
    return null;
  }

  function replaceDetails() {
    const found = findRecipe();
    if (!found) return;
    const data = found[1];
    const root = document.querySelector('.detailbody') || document.querySelector('.recipe-detail') || document.body;
    if (!root) return;

    // Stop repeated execution from rebuilding the same DOM forever.
    const signature = found[0] + '|' + data.steps.join('|');
    if (root.dataset.wcV2Signature === signature) return;

    let steps = root.querySelector('.steps');
    if (!steps) {
      const headings = Array.from(root.querySelectorAll('h2,h3,h4,strong')).filter(e => clean(e.textContent).toLowerCase() === 'steps');
      if (headings.length) steps = headings[0].parentElement;
    }
    if (!steps) return;

    // Replace the old short ordered list with the full V2 10-step list.
    const oldOl = steps.tagName.toLowerCase() === 'ol' ? steps : steps.querySelector('ol');
    if (oldOl) {
      oldOl.innerHTML = data.steps.map(s => '<li>' + s + '</li>').join('');
    } else {
      const ol = document.createElement('ol');
      ol.innerHTML = data.steps.map(s => '<li>' + s + '</li>').join('');
      steps.appendChild(ol);
    }

    // Find the ingredient section and append quantities without destroying the app's +list buttons.
    const headings = Array.from(root.querySelectorAll('h2,h3,h4,strong')).find(e => clean(e.textContent).toLowerCase().startsWith('ingredients'));
    if (headings && !root.querySelector('[data-wc-quantities]')) {
      const box = document.createElement('div');
      box.dataset.wcQuantities = '1';
      box.style.margin = '12px 0 18px';
      box.innerHTML = data.ingredients.map(([name, qty]) => `<div style="display:flex;justify-content:space-between;gap:12px;padding:7px 0;font-size:14px"><span>${name}</span><strong>${qty}</strong></div>`).join('');
      headings.parentElement.insertBefore(box, headings.nextSibling);
    }

    root.dataset.wcV2Signature = signature;
  }

  function addV1Label() {
    const logo = document.querySelector('.logo');
    if (logo && !logo.querySelector('[data-wc-v1]')) {
      const span = document.createElement('span');
      span.dataset.wcV1 = '1';
      span.textContent = ' V.1';
      span.style.fontSize = '10px';
      span.style.marginLeft = '3px';
      span.style.opacity = '.65';
      logo.appendChild(span);
    }
  }

  function run() {
    addV1Label();
    replaceDetails();
  }

  run();
  document.addEventListener('click', () => setTimeout(run, 50));
  document.addEventListener('DOMContentLoaded', run);
  let ticks = 0;
  const timer = setInterval(() => { run(); if (++ticks > 40) clearInterval(timer); }, 250);
})();
